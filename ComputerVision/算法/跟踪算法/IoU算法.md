# IoU算法详解

IoU（Intersection over Union，交并比）是用于衡量两个边界框（bounding box）重叠程度的指标。计算两个边界框交集面积与并集面积的比值，是目标检测、目标跟踪等任务中最常用的评估指标之一。IOU越大，两个框越重合，越可能是同一个目标。

IOU范围是[0,1]：IOU=1 说明完全重合；IOU=0 说明没有交集。

## 计算公式

给定两个边界框 $A$ 和 $B$：

- $A(x_{1A}, y_{1A}, x_{2A}, y_{2A})$
- $B(x_{1B}, y_{1B}, x_{2B}, y_{2B})$

IoU计算公式为：

$$IoU(A, B)=(A\cap B) / (A\cup B)$$

## 计算方法

```Python
def calculate_iou(boxA, boxB):
    """
    :params boxA: [x1, y1, x2, y2]
    :params boxB: [x1, y1, x2, y2]
    """
    # 确定交集区域的坐标
    inter_x1 = max(boxA[0], boxB[0])
    inter_y1 = max(boxA[1], boxB[1])
    inter_x2 = max(boxA[2], boxB[2])
    inter_y2 = max(boxA[3], boxB[3])

    # 计算交集面积
    inter_width = max(0, inter_x2 - inter_x1)
    inter_height = max(0, inter_y2 - inter_y1)
    inter_area = inter_width * inter_height

    # 计算各自面积
    area_A = (boxA[1] - boxA[0]) * (boxA[3] - boxA[2])
    area_B = (boxB[1] - boxB[0]) * (boxB[3] - boxB[2])

    # 计算并集面积
    union_area = area_A + area_B - inter_area

    # 计算IoU
    iou = inter_area / union_area if union_area > 0 else 0
```

## IoU应用场景

1. 帧间目标关联

    如何将当前帧的检测框与上一帧的跟踪目标关联？

    使用IoU作为相似性度量进行数据关联。

    ```Python
    def associate_detections_to_tracks(detections, tracks, iou_threshold=0.3):
        """
        使用IoU将检测关联到现有跟踪目标
        :param detections: 当前帧检测框 [[x1, y1, x2, y2, ...], ...]
        :param tracks: 跟踪目标列表 [KalmanFilter对象, ...]
        :param iou_threshold: 关联阈值
        :return: 匹配对, 未匹配检测, 未匹配跟踪
        """
        # 生成跟踪目标的预测框
        track_boxes = [track.predict() for track in tracks]

        # 计算IoU矩阵
        iou_matrix = np.zeros((len(detections), len(tracks)))
        for d, det in enumerate(detections):
            for t, trk in enumerate(track_boxes):
                iou_matrix[d, t] = calculate_iou(det[:4], trk[:4])

        # 使用匈牙利算法进行匹配
        row_idx, col_idx = linear_sum_assignment(-iou_matrix)

        matches, unmatched_dets, unmatched_trks = [], [], []

        # 筛选有效匹配
        for d, t in zip(row_idx, col_idx):
            if iou_matrix[d, t] >= iou_threshold:
                matches.append((d, t))
            else:
                unmatched_dets.append(d)
                unmatched_trks.append(t)
        
        # 添加未匹配项
        unmatched_dets += [d for d in range(len(detections)) if d not in row_idex]
        unmatched_trks += [t for t in range(len(tracks)) if t not in col_idx]

        return matches, unmatched_dets, unmatched_trks
    ```

2. 新目标初始化

   如何判断检测框代表新目标？

   当检测框与所有现有跟踪目标的IoU低于阈值时，视为新目标。

    ```Python
    def init_new_tracks(detections, tracks, iou_threshold=0.15):
        """
        初始化新跟踪目标
        :param detections: 未匹配检测框
        :param tracks: 现有跟踪目标
        :param iou_threshold: 新目标判定阈值
        :return: 新跟踪目标列表
        """
        new_tracks = []
        track_boxes = [track.get_state() for track in tracks]

        for det in detections:
            is_new = True

            # 检查是否与现有目标重叠
            for trk in track_boxes:
                iou = calculate_iou(det[:4], trk[:4])
                if iou > iou_threshold:
                    is_new = False
                    break

            # 创建新跟踪目标
            if is_new:
                new_track = KalmanFilter()
                new_track.init(det)
                new_tracks.append(new_track)

        return new_tracks
    ```

3. 跟踪目标终止

    何时终止丢失的目标？

    当目标连续多帧未匹配到检测框时终止。

    ```Python
    class Track:
        def __init__(self, detection):
            self.id = generate_unique_id()
            self.history = [detection]
            self.lost_count = 0
            self.active = True

        def update(self, detection):
            self.history.append(detection)
            self.lost_count = 0

        def mark_missed(self):
            self.lost_count += 1
            if self.lost_count > 5:
                self.active = False

        def is_actice(self):
            return self.active
    ```

4. 遮挡处理

   如何处理目标被遮挡的情况？

   使用IoU预测目标位置

   ```Python
   def handle_occlusions(tracks, detections, iou_threshold=0.4):
        """
        处理目标遮挡情况
        :param tracks: 当前跟踪目标
        :param detections: 当前帧检测框
        :param iou_threshold: 遮挡判定阈值
        """
        # 检测可能被遮挡的目标
        occluded_tracks = []
        for i, track in enumerate(tracks):
            if track.lost_count > 0:
                continue
            for detection in detections:
                iou = calculate_iou(track.get_predicted_box(), detection[:4])
                if iou < 0.1:
                    occluded_tracks.append(track)
                    break

        # 预测被遮挡目标位置
        for track in occluded_tracks:
            predicted_box = track.predict()

            # 检查是否有检测匹配预测位置
            best_iou = 0
            best_detection = None

            for detection in detections:
                iou = calculate_iou(predicted_box, detection[:4])
                if iou > best_iou:
                    best_iou = iou
                    best_detection = detection

            # 如果找到匹配，更新跟踪目标
            if best_iou > iou_thresh:
                track.update(best_detection)
                detections.remove(best_detection)
   ```

## 跟踪系统中的IoU优化策略

1. 自适应IoU阈值

    ```Python
    def adaptive_iou_threshold(track):
        """
        根据目标状态动态调整IoU阈值
        :param track: 跟踪目标对象
        :return: 自适应IoU阈值
        """
        base_threshold = 0.3

        # 基于目标速度调整
        if len(track.history) > 2:
            prev_box = track.history[-2]['bbox']
            current_box = track.history[-1]['bbox']
            speed = calculate_speed(prev_box, current_box)

            # 快速移动目标使用较低阈值
            if speed > 20:
                return max(0.15, base_threshold * 0.7)

        w, h = track.get_size()
        size_factor = min(1.0, (w * h) / 1000)
        return min(0.5, base_threshold + (0.2 * size_factor))
    ```

2. IoU与外观特征融合

    ```Python
    def combined_similarity(detection, track, iou_weight=0.7):
        """
        综合IoU和外观特征的相似度计算
        :param detection: 检测框
        :param track: 跟踪目标
        :param iou_weight: IoU权重
        :return: 综合相似度分数
        """
        # 计算IoU相似度
        iou_sim = calculate_iou(detection['bbox'], track.get_predicted_box())

        # 计算外观相似度（余弦相似度）
        appearance_sim = cosine_sumilarity(
            detection['feature'],
            track.get_apperance_feature()
        )

        # 动态权重调整
        if track.lost_count > 0:
            iou_weight = max(0.3, iou_weight - 0.2)

        # 综合相似度
        combined_sim = (iou_weight * iou_sum) + ((1 - iou_weight) * appearance_sim)
        return combined_sim
    ```

3. 基于IoU的轨迹平滑

    ```Python
    def smooth_trajectory(track, current_detection, iou_threshold=0.6):
        """
        使用IoU约束平滑轨迹
        :param track: 跟踪目标
        :param current_detection: 当前检测框
        :param iou_threshold: 平滑阈值
        """
        # 计算当前检测与预测的IoU
        predicted_box = track.predict()
        iou = calculate_iou(predicted_box, current_detection['bbox'])

        # 高IoU时使用卡尔曼滤波更新
        if iou > iou_threshold:
            track.update_with_kalman(current_detection)
        # 中等IoU时使用加权平均
        elif iou > 0.3:
            # 计算加权位置
            weight = iou / iou_threshold
            smoothed_box = [
                weight * predicted_box[i] + (1-weight) * current_detection['bbox'][i]
                for i in range(4)
            ]
            track.update(smoothed_box)
        # 低于IoU时直接使用检测框
        else:
            track.update(current_detection)
    ```

# FFmpeg

## 1、FFmpeg命令结构

    ffmpeg [全局参数] [输入参数] -i 输入文件 [输出参数] 输出文件

## 2、全局参数

    -y 覆盖输出文件（无需确认）
    -n 不覆盖输出文件（默认行为）
    -v loglevel 设置日志级别（-v quiet, -v error, -v info, -v debug）
    -hide_banner 隐藏FFmpeg版本和编译信息
    -stats 显示实时处理进度（默认启用）
    -fps_mode   用于控制视频流的时间戳同步模式
        vfr：可变帧率模式
        cfr：恒定帧率模式
        pfr：伪恒定帧率模式
    -rtsp_flags 指定在处理RTSP流时优先使用的协议
        udp：默认使用UDP协议传输数据
        tcp：使用TCP协议传输数据
        udp_multicast：使用UDP多播传输数据
        http：使用HTTP协议传输数据
        prefer_tcp：优先使用TCP协议
        listen：使FFmpeg监听RTSP命令和数据，而不是主动连接
        filter_src：过滤掉来自源地址的RTSP命令和响应
    -avioflags format的缓冲设置，默认为0，就是有缓冲
        direct： 使用直接I/O模式，减少对缓冲区的使用，从而提高性能
        direct_no_filesize：在直接I/O模式下，不预先获取文件大小。可用于处理大小未知或动态增长的文件，例如某些类型的流。
        ignore_min_frames：忽略解码器设置的最低帧数要求。尤其是想要更快地开始播放而不是等待足够帧。
        fastseek：启用快速搜索功能，对于需要频繁跳转的媒体文件有用，例如在视频播放器跳过广告。
        nobuffer：禁用内部缓冲。可以减少延迟，可能会影响性能和可靠性
        seek_to_global_pos：使用全局搜索而不是文件位置进行搜索。在进行跨文件搜索时特别有用，比如在多部分文件中搜索。
        offset：使用偏移量而非绝对位置进行搜索。对于处理有便宜但不从文件开始处读取的情况很有用
        read_seek：使用读操作来进行搜索
    -fflags 用于设置全局的编解码器标志
        +bitexact：启用比特精确模式，确保输出完全符合特定规范。
        +fast：启用快速模式，通常意味着使用一些近似算法来提高处理速度，但可能会牺牲一些质量。
        +genpts：强制生成时间戳
        +nofastseek：禁用快速随机访问优化
        +igndat：忽略数据包中DTS（解码时间戳），在某些封装格式中可能需要这个选项来正确处理时间戳。
        +discardcorrupt：丢弃损坏的数据包，避免因错误数据包而导致的编解码失败。
        +nobuffer：禁用帧缓冲，可以减少内存使用，但可能会影响编解码性能。
        +sortdts：对数据包进行排序，确保DTS（解码时间戳）是连续的。
        flush_packets：立即传递解码后的数据包
        nobuffer：禁用格式层缓冲
    -flags 指定编码器功能，控制压缩视频的质量和性能
        low_delay：启用解码器的低延迟模式
    -analyzeduration：用于指定在分析流中的音频和视频帧时应该花费的时间长度（微秒），主要用于提高处理速度或确保分析的准确性。
        增加值，将花费更多的时间来分析每个帧，有助于提高编码的准确性，但会增加处理时间。
        减少值，减少每个帧的分析时间，可能会导致编码参数不是最优的，但可以提高处理速度。

## 3、输入、输出参数

    -i input 指定输入文件（可多个输入）
    -f format 强制指定输入、输出格式（-f mp4, -f mpegts, -f image2pipe）
    -ss possition 跳转到指定时间（-ss 00:01:23, -ss 30秒）
    -t duration 限制处理时长（-t 10秒）
    -to position 处理到指定时间结束
    -pix_fmt：用于指定像素格式（Pixel Format）
        yuv420p：最常见的视频格式之一，用于标准定义视频，使用YUV颜色空间，其中Y是亮度分量，U和V是色度分量。420p表示色度采样比例为4:2:0，
            即色度分量在水平和垂直方向上都是每隔一行的采样。
        yuv422p：与yuv420p类似，但色度采样比例为4:2:2，即色度分量在水平方向上每隔一列采样，垂直方向上全采样。
        yuv444p：色度采样比例为4:4:4，即色度分量在水平和垂直方向上都全采样。
        rgb24：使用RGB颜色空间，每个像素由8位红绿蓝三个颜色分量。
        rgba：与rgb24类似，但每个像素还包括一个8位的alpha通道，用于表示透明度。
    -vcodec：用于指定视频编码器
        rawvideo：使用未压缩的视频流。
        copy：不重新编码，直接复制原视频流。
        libx264：使用x264
        libx265：使用x265
        libvpx-vp9：使用VP9编码器

## 4、视频参数

    -c:v codec 视频编码器（libx264, h265, vp9, copy不转码）
    -b:v bitrate 视频码率（-b:v 2M）
    -r fps 帧率（-r 30）
    -s resolution 分辨率（-s 1280x720）
    -vf filter 视频滤镜（如缩放、裁剪、旋转）
    -an 禁用音频输出

## 5、音频参数

    -c:a codec 音频编码器（aac, mp3, opus, copy不转码）
    -b:a bitrate 音频码率（-b:a 128k）
    -ar rate 采样率（-ar 44100）
    -ac channels 声道数（-ac 2立体声）
    -af filter 音频滤镜（音量调整、降噪）
    -vn 禁用视频输出

## 6、流映射

    -map n 选择输入文件的第n个流（-map 0:v 选第一个输入的视频流）
    -sn 禁用字幕流
    -metedata 修改元数据（-metadata title="My Video"）
    
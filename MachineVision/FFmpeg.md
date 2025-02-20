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
        direct_no_filesize：
    -fflags 

## 3、输入、输出参数
    -i input 指定输入文件（可多个输入）
    -f format 强制指定输入、输出格式（-f mp4, -f mpegts）
    -ss possition 跳转到指定时间（-ss 00:01:23, -ss 30秒）
    -t duration 限制处理时长（-t 10秒）
    -to position 处理到指定时间结束
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
    
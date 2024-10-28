class AudioPlayer:
    def play(self):
        print('audio play...')

class VideoPlayer:
    def play(self):
        print('video play...')

class MultiMediaPlayer:
    def __init__(self):
        self.audio_player = AudioPlayer()
        self.video_player = VideoPlayer()

    def audioPlay(self):
        self.audio_player.play()

    def videoPlay(self):
        self.video_player.play()

player = MultiMediaPlayer()
player.audioPlay()
player.videoPlay()
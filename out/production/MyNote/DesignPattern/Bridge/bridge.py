class Image:
    def parseFile(self, fileName: str):
        pass

class JPGImage(Image):
    def parseFile(self, fileName: str):
        return f'{fileName} 格式为JPG。'

class PNGImage(Image):
    def parseFile(self, fileName: str):
        return f'{fileName} 格式为PNG。'

class ImageImpl():
    def __init__(self, image: Image) -> None:
        self.image = image

    def doPain(text: str):
        pass

class MacImageImpl(ImageImpl):
    def doPain(self, text: str):
        parsed_file = self.image.parseFile(text)
        return f'Mac中的图像为{parsed_file}'

class WindowsImageImpl(ImageImpl):
    def doPain(self, text: str):
        parsed_file = self.image.parseFile(text)
        return f'Windows中的图像为{parsed_file}'

pngImage = PNGImage()
mac = MacImageImpl(pngImage)
print(mac.doPain("mac.png"))

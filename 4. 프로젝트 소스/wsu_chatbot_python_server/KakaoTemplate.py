class KakaoTemplate:
    def __init__(self):
        self.version = "2.0"


    def simpleTextComponent(self, text):
        return {
            "simpleText": {"text": text}
        }

    def basicCardComponent(self, link):
        return {
            "basicCard": {
                "description": "이 버튼을 눌러주세요",
                "buttons":[
                {
                    "action": "webLink",
                    "label": "바로가기",
                    "webLinkUrl": link
                }
                ]
                }
            }

    def basicCardComponent2(self, link1, link2):
        return {
            "basicCard": {
                "description": "이 버튼을 눌러주세요",
                "buttons": [
                    {
                        "action": "webLink",
                        "label": "교육과정 바로가기",
                        "webLinkUrl": link1
                    },
                    {
                        "action": "webLink",
                        "label": "졸업 후 진로 바로가기",
                        "webLinkUrl": link2
                    }
                ]
            }
        }

    def send_response(self, bot_resp):
        responseBody = {
            "version" : self.version,
            "template": {
                "outputs":[]
            }
        }
        responseBody['template']['outputs'].append(
            self.simpleTextComponent(bot_resp['Answer']))

        if bot_resp['AnswerImageUrl'] is not None:
            print(bot_resp['AnswerImageUrl'])
            var = "$" in bot_resp['AnswerImageUrl']
            if var is True:
                url1, url2 = bot_resp['AnswerImageUrl'].split("$")
                responseBody['template']['outputs'].append(
                    self.basicCardComponent2(url1, url2))
            elif var is False:
                responseBody['template']['outputs'].append(
                    self.basicCardComponent(bot_resp['AnswerImageUrl']))

        return responseBody
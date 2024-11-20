import time
from openai import OpenAI

from pymongo import MongoClient
# MongoDB 连接设置
client = MongoClient("mongodb://192.168.121.130:27017")  # 修改为你的MongoDB连接字符串
db = client["cookFood"]  # 替换为你的数据库名称
rdc = db["recipe"]  # 替换为你的集合名称
idc = db["ingredient"]

# 初始化OpenAI SDK
client = OpenAI(
    # 若没有配置环境变量，请用百炼API Key将下行替换为：api_key="sk-xxx",
    api_key="sk-xxxxx", 
    base_url="https://dashscope.aliyuncs.com/compatible-mode/v1",
)
qw_model = "qwen-plus-latest" # 模型列表：https://help.aliyun.com/zh/model-studio/getting-started/models
prompt = '帮我站在吃货的角度对以下的食材进行一对一描述,最好携带Emoji,用"|"分隔,个数对应,;不要有换行等多于字符.示例:羊肉🐑，那简直是味蕾上的温暖拥抱，每一口都是鲜美多汁、香而不腻的幸福滋味！'

def get_ingredients_desc(text, retry_num = 3):
    completion = {}
    while True :
        try: 
            completion = client.chat.completions.create(
            model=qw_model, 
            messages=[
                {'role': 'system', 'content': prompt},
                {'role': 'user', 'content': text}],
            # 超时时间
            timeout=30,
            )
            break
        except Exception as e:
            retry_num -= 1
            print("程序异常：", e)
            if retry_num == 0 : raise Exception("重试失败，请检查网络或者QPM/TPM达到限制")
            print("重试中，还剩%d次重试机会" % retry_num)
    return completion.choices[0].message.content.split("|")


def get_ingredient_list(current_page, limit = 100):
    res = idc.find().skip(current_page * limit).limit(limit)
    return list(res)

def get_ingredient_name_str(ingredient_list):
    str = ""
    for i in ingredient_list:
        str += (i["name"]) + ","
    # 去除最后一个逗号
    return str[:-1]

def set_ingredient_desc_list(ingredient_list, ingredient_desc_list):
    num = 0
    if len(ingredient_list) != len(ingredient_desc_list): raise Exception("食材数量与描述数量不一致")
    print(len(ingredient_list), len(ingredient_desc_list))
    for i in ingredient_list:
        idc.update_one({"_id": i["_id"]}, {"$set": {"description": ingredient_desc_list[num]}})
        num += 1

num = 56
limit = 25
retry_num = 3
while True:
    ingredient_list = get_ingredient_list(num, limit)
    ingredient_name_str = get_ingredient_name_str(ingredient_list)
    ingredient_desc_list = get_ingredients_desc(ingredient_name_str)
    print("当前为第%d页，共处理%d个数据" % (num, num * limit), ingredient_desc_list)
    # 暂停 1s
    time.sleep(1)
    try:
        set_ingredient_desc_list(ingredient_list, ingredient_desc_list)
        retry_num = 3
        num += 1
    except Exception as e:
        print("程序异常：", e)
        retry_num -= 1
        if retry_num == 0 : raise e
        print("重试中，还剩%d次重试机会" % retry_num)
        

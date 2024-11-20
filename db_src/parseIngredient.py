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
qw_model = "qwen-plus-0919" # 模型列表：https://help.aliyun.com/zh/model-studio/getting-started/models
prompt = '你是一个食材数据提炼员，你需要从文本中提炼出最基础食材(0加工食材)并去除重复项，忽略调味料或者香料！只留主食！输出以","分隔。注意：只输出食材数据，不要输出其他内容！每种食材中不要出现两种名称(不要有别名)！正确格式如："牛肉,羊肉"'

def get_ingredients_refined(text, retry_num = 3):
    completion = {}
    while True :
        try: 
            completion = client.chat.completions.create(
            model=qw_model, 
            messages=[
                {'role': 'system', 'content': prompt},
                {'role': 'user', 'content': text}],
            # 超时时间
            timeout=15,
            )
            break
        except Exception as e:
            retry_num -= 1
            print("程序异常：", e)
            if retry_num == 0 : raise Exception("重试失败，请检查网络或者QPM/TPM达到限制")
            print("重试中，还剩%d次重试机会" % retry_num)
    return completion.choices[0].message.content.split(",")


def get_ingredient_str_rough(current_page, limit = 100):
    res = rdc.aggregate([
        {
            '$unwind': "$recipeIngredient"
        },	
        {
            '$project': {'recipeIngredient': 1, '_id': 0}
        },
        {'$skip': current_page * limit},
        {'$limit': limit}, 
    ])
    str = ""
    for i in res:
        str += (i["recipeIngredient"]) + ","
    # 去除最后一个逗号
    return str[:-1]

def set_ingredient_list(ingredient_list):
    for ingredient in ingredient_list:
        if idc.find_one({"name": ingredient}): continue
        idc.insert_one({"name": ingredient})

num = 276
limit = 150
while True:
    ingredient_str = get_ingredient_str_rough(num, limit)
    ingredient_list = get_ingredients_refined(ingredient_str)
    print("当前为第%d页，共处理%d个数据" % (num, num * limit), ingredient_list)
    # 暂停 100ms
    time.sleep(1)
    set_ingredient_list(ingredient_list)
    num += 1

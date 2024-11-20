<p align="center">
  <a href="#">
    <img src="images/logo.svg" alt="Logo" width="80" height="80">
  </a>

  <h1 align="center">COOKFOOD 🍲</h1>
  <p align="center">
    让我们一起来做饭吧！
  </p>



## 项目简介 ✨  
**COOKFOOD** 是一个为健康生活打造的终极数据库系统！  
无论你是健身狂热者、减肥小能手，还是想偷偷学会一两个拿手菜，这个项目都会让你的健康饮食变得轻松、有趣而且高效。  

我们提供了**丰富的食谱**、**详细的营养信息**和**贴心的推荐服务**，帮助你吃得对、吃得美。放心，COOKFOOD 是个懂得生活的数据库😉。  

---

## 项目功能 🚀  
- **超强食谱库**：不论是低卡健身餐还是幸福感满满的甜点，总有适合你的那一道菜！  
- **神奇食材指南**🏗️：告诉你每种食材的营养奥秘，是减肥好帮手，还是能让你秒变大厨的秘密武器？一查便知！  
- **个性化推荐**🏗️：基于你的健康目标（减肥、增肌、增快乐？）量身推荐绝佳搭配。  
- **精准分类查询**🏗️：快速找到符合你需求的菜谱，按热量、菜系、目标人群任意筛选。  
- **用户互动**🏗️：打分、收藏、评论……是时候展现真正的吃货素养了！  

---

## 谁能用它？👨‍🍳👩‍🍳  
- **减脂爱好者**：想找一碗不让你内疚的好吃的面？COOKFOOD 了解你。  
- **增肌达人**：你的鸡胸肉再也不会无聊了。  
- **家庭小厨神**：让你的家人多说几句“好吃！”。  
- **美食初学者**：从零开始，COOKFOOD 也能陪你变成大厨。  

---

## 项目架构 🏗️  

### 数据库技术  
- **类型**：NoSQL 文档型数据库  
- **工具**：MongoDB  

### 数据模型  
我们设计了以下集合，每一部分都代表着用户在厨房里的“超级助手”：  
1. **recipes（食谱集合）**：存放所有食谱及其做法，就像一本活的“美味圣经”。  
2. **ingredients（食材集合）**：了解食材的营养价值，为你的健康加点料！  
3. **users（用户集合）**：记录用户偏好，帮你吃得更开心。  
4. **reviews（评价集合）**：吃货们的真实心声，你也可以在这里找到灵感！  

---

## 目录结构 📁  
```plaintext
COOKFOOD/
├── cookFood_backend		  # 后端的内容
├── coodFood_front            # 前端的内容
│
├── data/					  # 这个文件夹的数据不在仓库哦，需要下载往下翻↓
│   ├── recipes.json          # 样例食谱数据（清单值得你翻翻）
│   └── ingredients.json      # 样例食材数据（谁是热量炸弹，一看就知道） 
│
├── db_src/
│   ├── inDb.py               # 从数据集写入数据库
│   ├── parseIngredient.py    # 使用AI大模型从recipe集合中清洗出ingredient
│   ├── insertIngredientDesc.py # 使用AI大模型从ingredient集合生成出对于desc  <--这里还可以扩展很多内容
|
├── images					  # 一些有用的图片的文件夹
└── README.md				  # 你正在阅读的文件

```

---

## 食用方法 🍴  

### 安装依赖  
1. 克隆项目到本地：
   ```bash
   git clone https://github.com/your-repo/cookfood.git
   ```

### 数据库准备  
1. 启动 MongoDB：  
   ```bash
   mongod --dbpath /your/data/path
   ```
2. 将数据导入数据库：  
   ```bash
   mongoimport --db cookfood --collection recipes --file data/recipes.json
   mongoimport --db cookfood --collection ingredients --file data/ingredients.json
   ```

### 然后配置好前后端就开始使用啦！  
---

## 数据来自哪里？🧐  
- 来自于[HyperAI超神经](https://hyper.ai/)：XiaChuFang Recipe Corpus 下厨房食谱语料库
- 需要现成json数据在这里：https://www.123684.com/s/7B4njv-10Fjh?提取码:FkwL

---

## 未来的“美味计划” 🍳  
- 开发一个酷炫的用户界面，让数据库变成“美食界的 Instagram”。  
- 加入菜谱分享社区，你的创意可以拯救厨房里的无聊。  
- 接入AI大模型优化推荐、自动获取生成食材数据，让每一顿饭都变成幸福的艺术！  

---

**Let’s COOKFOOD! 健康美味，触手可及！🎉**  
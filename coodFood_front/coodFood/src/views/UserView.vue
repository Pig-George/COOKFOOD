<template>
  <div>
    <a-flex justify="center" class="title"> 我的 </a-flex>
  </div>
  <div class="user-info-box">
    <a-flex justify="space-between" align="center" class="user-info-item">
      <h3>ID</h3>
      <text>{{ userInfo._id }}</text>
    </a-flex>
    <a-flex justify="space-between" align="center" class="user-info-item">
      <h3>昵称</h3>
      <a-input
        al
        class="ipt"
        v-model:value="userInfo.name"
        placeholder="请输入昵称"
        :bordered="false"
        @blur="(e) => updateUserName(e.target.value)"
      />
    </a-flex>
    <a-flex justify="space-between" align="center" class="user-info-item">
      <h3>最新更新时间</h3>
      <text>{{ formatTime(userInfo.updateTime) }}</text>
    </a-flex>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { message } from 'ant-design-vue'
const userInfo = ref({})
Apis.user.get_user({}).then((res) => {
  userInfo.value = res
})

const formatTime = (time) => {
  return new Date(time).toLocaleString()
}

const updateUserName = async (name) => {
  await Apis.user.put_user({ params: { name } })
  message.success('修改成功')
}
</script>
<style lang="less" scoped>
.title {
  margin-top: 30px;
  font-size: 20px;
}
.user-info-box {
  margin: 30px 20vw;
  .ipt {
    width: 40%;
    text-align: right;
    padding-right: 0;
  }
  .user-info-item {
    border-top: #e8e8e8 solid 1px;
  }
  :first-child {
    border-top: 0;
  }
}
</style>

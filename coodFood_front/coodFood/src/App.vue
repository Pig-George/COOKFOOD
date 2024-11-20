<script setup>
import { reactive, ref, watch, KeepAlive } from 'vue'
import { HomeOutlined, UserOutlined } from '@ant-design/icons-vue'
import router from '@/router'

const data = reactive([
  {
    value: 'home',
    payload: {
      icon: HomeOutlined,
      title: '首页',
    },
  },
  {
    value: 'user',
    payload: {
      icon: UserOutlined,
      title: '我的',
    },
  },
])

const isHide = ref(false)

watch(router.currentRoute, (to) => {
  value.value = to.name
})

const value = ref(data[0].value)
const token = {
  borderRadius: 10,
}
Apis.user.post_user({}).then()
</script>

<template>
  <a-config-provider
    :theme="{
      token,
    }"
  >
    <header>
      <nav class="nav" :style="{ bottom: isHide ? '-100px' : '10vh' }">
        <a-segmented
          size="large"
          v-model:value="value"
          :options="data"
          @change="router.push(value)"
        >
          <template #label="{ payload }">
            <div class="a-segmented-label">
              <component :is="payload.icon" style="font-size: 30px" />
              <div class="title">{{ payload.title }}</div>
            </div>
          </template>
        </a-segmented>
      </nav>
    </header>
    <router-view v-slot="{ Component }">
      <keep-alive>
        <component :is="Component" v-model:isHide="isHide" />
      </keep-alive>
    </router-view>
  </a-config-provider>
</template>

<style scoped lang="less">
.nav {
  display: flex;
  justify-content: center;
  position: fixed;
  width: 100%;
  bottom: 10vh;
  display: flex;
  align-items: center;
  pointer-events: all;
  z-index: 999;
  transition: bottom 0.5s;
  .a-segmented-label {
    margin: 5px;
    width: 80px;
    .title {
      line-height: 16px;
      height: 20px;
      user-select: none;
    }
  }
}
</style>

<style lang="less">
.nav {
  .ant-segmented {
    backdrop-filter: blur(5px);
    background: #b8b8b83d;
  }
}
</style>

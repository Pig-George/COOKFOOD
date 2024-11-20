<script setup>
import { ref, reactive, onMounted, defineModel } from 'vue'
import { useRequest } from 'alova/client'
import { ExportOutlined, SearchOutlined } from '@ant-design/icons-vue'
import SearchModal from '@/components/SearchModal.vue'
import { Waterfall } from 'vue-waterfall-plugin-next'
import 'vue-waterfall-plugin-next/dist/style.css'
import { LoadingOutlined } from '@ant-design/icons-vue'
import { h } from 'vue'
const indicator = h(LoadingOutlined, {
  style: {
    fontSize: '24px',
  },
  spin: true,
})

const isHide = defineModel('isHide', {
  type: Boolean,
})

const onScroll = (() => {
  let _scrollTop = 0
  return ({ target: { scrollTop } }) => {
    if (_scrollTop < scrollTop) isHide.value = true
    else isHide.value = false
    _scrollTop = scrollTop
  }
})()

const open = ref(false)
const openDrawer = ref(false)
const repice = ref(null)
const onOpenModal = () => {
  open.value = true
}

const listData = reactive([])
const { loading: listLoading, send: getListDataFun } = useRequest(
  (params) => Apis.recipe.get_recipe_list({ params }),
  {
    immediate: false,
  },
)

const listRef = ref(null)
const footerRef = ref(null)
let observer = null
onMounted(() => {
  observer = new IntersectionObserver(
    (entries) => {
      if (entries[0].isIntersecting) {
        page++
        getData()
      }
    },
    { threshold: 0.5, root: listRef.value.$el },
  )
  setTimeout(() => {
    observer.observe(footerRef.value)
  }, 800)
})

let page = 1
const getData = () => {
  if (listLoading.value) return
  getListDataFun({ page, size: 20 }).then((res) => {
    listData.push(...res)
  })
}
getData()
</script>
<template>
  <div>
    <a-flex vertical style="height: 100vh">
      <a-flex justify="space-between" align="center" flex="align-items: center;">
        <a-tooltip placement="right">
          <template #title>分享</template>
          <ExportOutlined class="icon" />
        </a-tooltip>
        <div class="title">COOKFOOD</div>
        <a-tooltip placement="left">
          <template #title>搜索</template>
          <SearchOutlined class="icon" @click="onOpenModal" />
        </a-tooltip>
      </a-flex>
      <div class="content">
        <div ref="listRef" class="list" @scroll="onScroll">
          <Waterfall :list="listData" width="260" :gutter="20">
            <template #default="{ item }">
              <a-card :title="item.name">
                <template #extra
                  ><a
                    harf="#"
                    @click="
                      () => {
                        openDrawer = true
                        repice = item
                      }
                    "
                    >更多</a
                  ></template
                >
                <div>
                  食材：
                  <ul>
                    <li v-for="(ingredient, index) in item.recipeIngredient" :key="index">
                      {{ ingredient }}
                    </li>
                  </ul>
                </div>
              </a-card>
            </template>
          </Waterfall>
          <div ref="footerRef" style="padding: 100px 0">
            <a-flex justify="center">
              <a-spin :indicator="indicator" />
            </a-flex>
          </div>
        </div>
      </div>
    </a-flex>
    <SearchModal v-model:open="open" />
    <a-drawer
      width="70vw"
      placement="right"
      :closable="false"
      v-model:open="openDrawer"
      @close="onClose"
    >
      <a-descriptions :title="repice.name" layout="vertical" bordered>
        <a-descriptions-item label="菜名" :span="2">{{ repice.name }}</a-descriptions-item>
        <a-descriptions-item label="简称">{{ repice.dish || '无' }}</a-descriptions-item>
        <a-descriptions-item label="描述" :span="3">{{
          repice.description || '无'
        }}</a-descriptions-item>
        <a-descriptions-item label="食材" :span="3">
          <ul>
            <li v-for="(ingredient, index) in repice.recipeIngredient" :key="index">
              {{ ingredient }}
            </li>
          </ul>
        </a-descriptions-item>
        <a-descriptions-item label="做法" :span="3">
          <ol>
            <li v-for="(step, index) in repice.recipeInstructions" :key="index">{{ step }}</li>
          </ol>
        </a-descriptions-item>
      </a-descriptions>
    </a-drawer>
  </div>
</template>
<style lang="less" scoped>
.icon {
  font-size: 24px;
  margin: 10px;
}
</style>
<style lang="less">
.content {
  margin: 30px auto;
  position: relative;
  overflow: hidden;
  &::before,
  &::after {
    position: absolute;
    content: '';
    display: block;
    height: 0;
    width: 100%;
    z-index: 1;
  }
  &::before {
    top: 0;
    box-shadow: 0px 5px 10px 15px #ffffff;
  }
  &::after {
    bottom: 0;
    box-shadow: 0px -5px 10px 15px #ffffff;
  }
  .list {
    overflow-x: hidden;
    padding: 20px 0;
    width: 80vw;
    height: calc(100vh - 44px);
    .card-desc {
      word-wrap: break-word;
    }
    &::-webkit-scrollbar {
      width: 10px; /*高宽分别对应横竖滚动条的尺寸*/
      height: 1px;
    }
    &::-webkit-scrollbar-thumb {
      /*滚动条里面小方块*/
      border-radius: 10px;
      box-shadow: inset 0 0 5px rgba(206, 206, 206, 0.2);
      background: #aaaaaaa7;
    }
    &::-webkit-scrollbar-track {
      /*滚动条里面轨道*/
      opacity: 0;
    }
  }
}
</style>

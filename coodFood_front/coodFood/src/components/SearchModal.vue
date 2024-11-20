<template>
  <a-modal class="search-modal" :closable="false">
    <div class="modal-search-box">
      <a-space direction="vertical" style="width: 100%">
        <a-input-search
          ref="searchInput"
          v-model:value.trim="srearchValue"
          placeholder="搜索食材，多种食材用'|'分隔"
          enter-button
          size="large"
          allowClear
          @search="onRefresh"
        />
        <a-list
          :style="{
            overflowY:
              (searchRecipeLoading || searchIngredientLoading) && curPage === 1 ? 'hidden' : 'auto',
          }"
          ref="aListRef"
          item-layout="horizontal"
          :data-source="searchedData"
          :locale="{ emptyText: '暂无搜索结果' }"
          :loading="(searchRecipeLoading || searchIngredientLoading) && curPage === 1"
          bordered
        >
          <template #header>
            <a-segmented v-model:value="smValue" :options="searchOpt" block />
          </template>
          <template #renderItem="{ item }">
            <a-list-item>
              <a-list-item-meta :description="item.description">
                <template #title>
                  <a href="https://www.antdv.com/">{{ item.name }}</a>
                </template>
              </a-list-item-meta>
            </a-list-item>
          </template>
          <template #loadMore v-if="searchedData.length > 0">
            <a-flex v-show="!isLastPage" justify="center" style="padding: 10px 0">
              <a-spin />
            </a-flex>
            <div v-if="isLastPage" style="padding: 10px 0; text-align: center">没有更多了</div>
          </template>
        </a-list>
      </a-space>
    </div>
  </a-modal>
</template>

<script setup>
import { ref, reactive, onMounted, watch, useAttrs, onUpdated } from 'vue'
import { useRequest } from 'alova/client'

let aListRef
onMounted(() => {
  aListRef = ref(null)
})
const searchInput = ref(null)
const srearchValue = ref('')
const searchOpt = reactive([
  { label: '菜谱', value: 'recipe' },
  { label: '食材', value: 'ingredient' },
])
const smValue = ref(searchOpt[0].value)

const searchedData = reactive([])
const curPage = ref(1)
const isLastPage = ref(false)

watch(smValue, () => {
  onRefresh()
})

const { send: searchRecipeFun, loading: searchRecipeLoading } = useRequest(
  (params) =>
    Apis.recipe.get_recipe_keyword({
      params,
    }),
  {
    immediate: false,
  },
)

const { send: searchIngredientFun, loading: searchIngredientLoading } = useRequest(
  (params) =>
    Apis.ingredient.get_ingredient_description({
      params,
    }),
  {
    immediate: false,
  },
)

let observer = null
onUpdated(() => {
  const { open } = useAttrs()
  if (open) {
    setTimeout(() => {
      searchInput.value.focus()
    }, 500)
    observer =
      observer ||
      new IntersectionObserver(
        () => {
          onSearch()
        },
        { threshold: 0.5, root: aListRef.value.$el },
      )
  }
})

const onRefresh = async () => {
  curPage.value = 1
  await onSearch()
  setTimeout(() => {
    if (observer) {
      observer.disconnect()
      observer.observe(aListRef.value.$el.lastElementChild)
    }
  }, 500)
}

const onSearch = async () => {
  if (searchRecipeLoading.value || searchIngredientLoading.value) return
  const params = {
    keys: srearchValue.value.split('|'),
    page: curPage.value++,
    fields: ['name', 'description'],
  }
  console.log(params)
  const searchDataFun = smValue.value === 'recipe' ? searchRecipeFun : searchIngredientFun
  const res = await searchDataFun(params)
  const { dataList, page, pageSize } = res
  if (page === 1) {
    searchedData.splice(0)
  }
  searchedData.push(...dataList)
  isLastPage.value = dataList.length < pageSize
}
</script>
<style lang="less">
.search-modal {
  .ant-modal-footer {
    display: none;
  }
  .ant-list {
    height: 70vh;
    overflow-y: auto;
  }
}
</style>

import { createAlova } from 'alova';
import fetchAdapter from 'alova/fetch';
import vueHook from 'alova/vue';
import { message } from 'ant-design-vue';
import { createApis, withConfigType } from './createApis';
import FingerprintJS from '@fingerprintjs/fingerprintjs';
const fingerLocalName = 'fingerprint';
export const alovaInstance = createAlova({
  baseURL: 'http://localhost:8080',
  statesHook: vueHook,
  requestAdapter: fetchAdapter(),
  beforeRequest: async method => {
    let fingerprint = localStorage.getItem(fingerLocalName);
    if (!fingerprint) {
      const fp = await FingerprintJS.load();
      ({ visitorId: fingerprint } = await fp.get());
      localStorage.setItem(fingerLocalName, fingerprint);
    }
    method.config.headers['X-Fingerprint'] = fingerprint;
  },
  responded: {
    onSuccess: async (response, method) => {
      if (response.status >= 400) {
        throw new Error(response.statusText);
      }
      const json = await response.json();
      if (json.code !== 200) {
        // 抛出错误或返回reject状态的Promise实例时，此请求将抛出错误
        throw new Error(json.msg);
      }

      // 解析的响应数据将传给method实例的transform钩子函数，这些函数将在后续讲解
      return json.data;
    },
    onError: res => {
      console.error(res);
      message.error(res.msg || '请求失败了');
    }
  }
});

export const $$userConfigMap = withConfigType({});

/**
 * @type { Apis }
 */
const Apis = createApis(alovaInstance, $$userConfigMap);

export default Apis;

/* tslint:disable */
/* eslint-disable */
/**
 * cookFood - version 1.0.0
 *
 *
 *
 * OpenAPI version: 3.0.1
 *
 *
 * NOTE: This file is auto generated by the alova's vscode plugin.
 *
 * https://alova.js.org/devtools/vscode
 *
 * **Do not edit the file manually.**
 */
import { Method } from 'alova';
import apiDefinitions from './apiDefinitions';
/**
 * @typedef {import('alova').AlovaGenerics} AlovaGenerics
 */
/**
 *
 * @param {(string|symbol)[]} array
 * @param {Alova<AlovaGenerics>} alovaInstance
 * @param {any} configMap
 * @returns {()=>void}
 */
const createFunctionalProxy = (array, alovaInstance, configMap) => {
  // create a new proxy instance
  return new Proxy(function () {}, {
    get(_, property) {
      // record the target property, so that it can get the completed accessing paths
      array.push(property);
      // always return a new proxy to continue recording accessing paths.
      return createFunctionalProxy(array, alovaInstance, configMap);
    },
    apply(_, __, [config]) {
      const apiPathKey = array.join('.');
      const apiItem = apiDefinitions[apiPathKey];
      if (!apiItem) {
        throw new Error(`the api path of \`${apiPathKey}\` is not found`);
      }
      const mergedConfig = {
        ...configMap[apiPathKey],
        ...config
      };
      const [method, url] = apiItem;
      const pathParams = mergedConfig.pathParams;
      const urlReplaced = url.replace(/\{([^}]+)\}/g, (_, key) => {
        const pathParam = pathParams[key];
        return pathParam;
      });
      delete mergedConfig.pathParams;
      let data = mergedConfig.data;
      if (Object.prototype.toString.call(data) === '[object Object]' && typeof FormData !== 'undefined') {
        let hasBlobData = false;
        const formData = new FormData();
        for (const key in data) {
          formData.append(key, data[key]);
          if (data[key] instanceof Blob) {
            hasBlobData = true;
          }
        }
        data = hasBlobData ? formData : data;
      }
      return new Method(method.toUpperCase(), alovaInstance, urlReplaced, mergedConfig, data);
    }
  });
};
/**
 *
 * @param {Alova<AlovaGenerics>} alovaInstance
 * @param {any} configMap
 * @returns { Apis }
 */
export const createApis = (alovaInstance, configMap) => {
  const Apis = new Proxy(
    {},
    {
      get(_, property) {
        return createFunctionalProxy([property], alovaInstance, configMap);
      }
    }
  );
  // define global variable `Apis`
  globalThis.Apis = Apis;
  return Apis;
};
/**
 * @template T
 * @typedef {import('alova').AlovaMethodCreateConfig<typeof import('./index')['alovaInstance'] extends import('alova').Alova<infer AG> ? AG : any, any, T>} MethodConfig
 */
/**
 * @typedef {{ [P in keyof typeof import('./apiDefinitions').default]?: MethodConfig<P extends `${infer Tag}.${infer Url}` ? Parameters<Parameters<Apis[Tag][Url]>[0]['transform']>[0] : any> }} MethodsConfigMap
 */
/**
 * @template {MethodsConfigMap} Config
 * @param {Config} config
 */
export const withConfigType = config => config;

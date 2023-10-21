import axios from "axios";
import $httpApi from "@/api/index";

/**
 * 获取课程详情
 * @param dictType
 * @param callBack
 */
export function startExam(testPaperId) {
  return axios.get($httpApi.httpUrl("/student/exam/start"), {
    params: {
      testPaperInfoId: testPaperId
    }
  });
}

/**
 * 试卷详情
 * @param testPaperId
 * @returns {AxiosPromise<any>}
 */
export function getQuestionListById(testPaperId) {
  return axios.get(
    $httpApi.httpUrl("/student/testPaperInfo/" + testPaperId + "/question")
  );
}

/**
 * 试卷详情
 * @param testPaperId
 * @returns {AxiosPromise<any>}
 */
export function getById(testPaperId) {
  return axios.get($httpApi.httpUrl("/student/testPaperInfo/" + testPaperId));
}

/**
 * 清除试卷考试缓存
 * @param testPaperId
 * @returns {AxiosPromise<any>}
 */
export function clearPaperExamTime(testPaperId) {
  return axios.delete(
    $httpApi.httpUrl("/student/testPaperInfo/examTime/countDown/" + testPaperId)
  );
}

/**
 * 加入考试监控
 * @param params
 */
export function addPaperMonitor(params) {
  return axios.post($httpApi.httpUrl("/student/exam/monitor"), params);
}

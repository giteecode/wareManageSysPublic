import request from "@/utils/request";

// 查询货位列表
export function listLocation(query) {
  return request({
    url: "/base/location/list",
    method: "get",
    params: query,
  });
}

// 查询货位列表所有
export function listAllLocation(query) {
  return request({
    url: "/base/location/listAll",
    method: "get",
    params: query,
  });
}

// 查询货位详细
export function getLocation(locationId) {
  return request({
    url: "/base/location/" + locationId,
    method: "get",
  });
}

// 新增货位
export function addLocation(data) {
  return request({
    url: "/base/location",
    method: "post",
    data: data,
  });
}

// 修改货位
export function updateLocation(data) {
  return request({
    url: "/base/location",
    method: "put",
    data: data,
  });
}

// 删除货位
export function delLocation(locationId) {
  return request({
    url: "/base/location/" + locationId,
    method: "delete",
  });
}

// 打印货位
export function printLocation(locationId) {
  return request({
    url: "/base/location/printLocation/" + locationId,
    method: "get",
    responseType: "arraybuffer",
    headers: {
      "Content-Type": "application/json",
    },
  });
}

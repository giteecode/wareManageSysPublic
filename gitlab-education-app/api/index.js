import store from '@/store'
export function hasLogin (permission) {
   let tokenInfo = store.getters['user/getTokenInfo']
   if (tokenInfo) {
	   return true
   }
   return false
}
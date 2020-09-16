import BASEURL from '@/httpconfig/api.js'
import axios from 'axios'

axios.defaults.timeout = 600000000 //600000s
axios.defaults.withCredentials = true

axios.defaults.headers.post['Content-Type'] =
  'application/x-www-form-urlencoded;charset=UTF-8'

// 默认地址
axios.defaults.baseURL = BASEURL.apiUrl

export default axios

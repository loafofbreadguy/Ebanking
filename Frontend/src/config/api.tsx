const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

const API_ENDPOINTS = {
  BALANCE: `${API_BASE_URL}/wallets/balance`,
  LOGIN: `${API_BASE_URL}/auth/login`,
  REGISTER:  `${API_BASE_URL}/user/register`,
  REFRESH_TOKEN:`${API_BASE_URL}/auth/refresh`,
  AUTH:`${API_BASE_URL}/auth/me`,
  TRANSACTION:`${API_BASE_URL}/transactions/getalltransaction`,
  TRANSFER: `${API_BASE_URL}/transactions/transfer`,
  WITHDRAW: `${API_BASE_URL}/wallets/withdraw`,
  DEPOSIT: `${API_BASE_URL}/wallets/deposit`,
  CREATE_COMPLAINT:`${API_BASE_URL}/complaints/create`,
  USER:`${API_BASE_URL}/user/fetch/`
  
};

export default API_ENDPOINTS;
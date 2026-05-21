import axios from "axios";
import API_ENDPOINTS from "../../config/api";

export const refreshToken = async () => {
  const res = await axios.post(
    API_ENDPOINTS.REFRESH_TOKEN,
    {},
    {
      withCredentials: true,
    }
  );

  return res.data;
};
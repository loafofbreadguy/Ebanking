import axios from "axios";
import API_ENDPOINTS from "../../config/api";

interface LoginApiResponse {
  success: boolean;
  message: string;
}

export const login = async (payload: {
  email: string;
  password: string;
}): Promise<LoginApiResponse> => {
  try {
    const res = await axios.post<LoginApiResponse>(
      API_ENDPOINTS.LOGIN,
      payload,
      { withCredentials: true }
    );

    return res.data;
  } catch (error: any) {
    return {
      success: false,
      message: error?.response?.data?.message || "Login Failed",
    };
  }
};
import axios from "axios";
import API_ENDPOINTS from "../../config/api";

interface RegisterApiResponse {
  success: boolean;
  message: string;
}

export const register = async (payload: {
  name: string;
  email: string;
  password: string;
}): Promise<RegisterApiResponse> => {
  try {
    const res = await axios.post<RegisterApiResponse>(
      API_ENDPOINTS.REGISTER,
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
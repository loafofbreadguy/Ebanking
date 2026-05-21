import axios from "axios";
import API_ENDPOINTS from "../../config/api";

interface AuthResponse {
  email: string;
  authenticated: boolean;
}

export const checkAuth = async (): Promise<AuthResponse | null> => {
  try {
    const response = await axios.get<AuthResponse>(
      API_ENDPOINTS.AUTH,
      {
        withCredentials: true,
      }
    );

    return response.data;
  } catch (error) {
    return null;
  }
};
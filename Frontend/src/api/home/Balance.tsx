import axios from "axios";
import API_ENDPOINTS from "../../config/api";
import { refreshToken } from "../auth/refresh";
import { toast } from "../../components/toast/toast";

export const Balance = async () => {
  try {
    const res = await axios.get(API_ENDPOINTS.BALANCE, {
      withCredentials: true,
    });

    const data = res.data;

    // handle backend "fake errors"
    if (data?.success === false) {
      const message = data?.message?.toLowerCase() || "";

      if (message.includes("rate limit")) {
        toast.warning(
          "Too many requests",
          "Please wait a few seconds before trying again."
        );
        return null;
      }

      return null;
    }

    return data?.data?.balance ?? null;
  } catch (error: any) {
    const status = error?.response?.status;

    if (status === 401) {
      await refreshToken();

      const retry = await axios.get(API_ENDPOINTS.BALANCE, {
        withCredentials: true,
      });

      const retryData = retry.data;

      if (retryData?.success === false) return null;

      return retryData?.data?.balance ?? null;
    }

    return null;
  }
};
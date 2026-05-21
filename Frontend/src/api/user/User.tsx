import axios from "axios";
import API_ENDPOINTS from "../../config/api";
import { toast } from "../../components/toast/toast";
import { refreshToken } from "../auth/refresh";

export const user = async () => {
  try {
    const res = await axios.get(API_ENDPOINTS.USER, {
      withCredentials: true,
    });

    const data = res.data;

    if (data?.success === false) {
      const message = data?.message?.toLowerCase() || "";

      if (message.includes("rate limit")) {
        toast.warning(
          "Too many requests",
          "Please wait a few seconds before trying again."
        );
      }

      return null;
    }

    return data?.data ?? null;
  } catch (error: any) {
    const status = error?.response?.status;

    if (status === 401) {
      try {
        await refreshToken();

        const retry = await axios.get(API_ENDPOINTS.USER, {
          withCredentials: true,
        });

        const retryData = retry.data;

        if (retryData?.success === false) {
          return null;
        }

        return retryData?.data ?? null;
      } catch {
        return null;
      }
    }

    return null;
  }
};
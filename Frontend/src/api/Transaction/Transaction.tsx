import axios from "axios";
import API_ENDPOINTS from "../../config/api";
import { refreshToken } from "../auth/refresh";
import { toast } from "../../components/toast/toast";

export const transaction = async (page = 0, size = 10) => {
  try {
    const res = await axios.get(
      `${API_ENDPOINTS.TRANSACTION}?page=${page}&size=${size}`,
      {
        withCredentials: true,
      }
    );

    const data = res.data;

    // backend fake errors
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

    return {
      transactions: data?.data?.content || [],
      currentPage: data?.data?.number || 0,
      totalPages: data?.data?.totalPages || 0,
      totalElements: data?.data?.totalElements || 0,
      size: data?.data?.size || 10,
      first: data?.data?.first || false,
      last: data?.data?.last || false,
    };
  } catch (error: any) {
    const status = error?.response?.status;

    if (status === 401) {
      try {
        await refreshToken();

        const retry = await axios.get(
          `${API_ENDPOINTS.TRANSACTION}?page=${page}&size=${size}`,
          {
            withCredentials: true,
          }
        );

        const retryData = retry.data;

        if (retryData?.success === false) return null;

        return {
          transactions: retryData?.data?.content || [],
          currentPage: retryData?.data?.number || 0,
          totalPages: retryData?.data?.totalPages || 0,
          totalElements: retryData?.data?.totalElements || 0,
          size: retryData?.data?.size || 10,
          first: retryData?.data?.first || false,
          last: retryData?.data?.last || false,
        };
      } catch {
        return null;
      }
    }

    return null;
  }
};
import axios from "axios";
import API_ENDPOINTS from "../../config/api";
import { refreshToken } from "../auth/refresh";
import { toast } from "../../components/toast/toast";

export const deposit = async (amount: number) => {
  try {
    if (isNaN(amount) || amount <= 0) {
      toast.error("Invalid amount", "Enter a valid deposit amount");
      return null;
    }

    const res = await axios.post(
      API_ENDPOINTS.DEPOSIT,
      {},
      {
        params: { amount },
        withCredentials: true,
      }
    );

    const data = res.data;

    if (data?.success === false) {
      const message = data?.message?.toLowerCase() || "";

      if (message.includes("rate limit")) {
        toast.warning(
          "Too many requests",
          "Please wait a few seconds before trying again."
        );
      } else {
        toast.error(
          "Deposit failed",
          "Error processing request"
        );
      }

      return null;
    }

    toast.success("Deposit successful", data.data);
    return data;

  } catch (error: any) {
    if (error?.response?.status === 401) {
      try {
        await refreshToken();

        const retry = await axios.post(
          API_ENDPOINTS.DEPOSIT,
          {},
          {
            params: { amount },
            withCredentials: true,
          }
        );

        const retryData = retry.data;

        if (retryData?.success === false) {
          return null;
        }

        toast.success("Deposit successful", retryData.data);
        return retryData;

      } catch {
        return null;
      }
    }

    toast.error(
      "Request failed",
      error?.response?.data?.message || "Error processing request"
    );

    return null;
  }
};
import axios from "axios";
import API_ENDPOINTS from "../../config/api";
import { refreshToken } from "../auth/refresh";
import { toast } from "../../components/toast/toast";

export const Transfer = async (
  receiverUserId = 0,
  amount = 10,
  description = "Blank"
) => {
  try {
    const res = await axios.post(
      API_ENDPOINTS.TRANSFER,
      {},
      {
        params: {
          receiverUserId,
          amount,
          description,
        },
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
      }
      else{
        toast.error(
          "Transfer unSuccessfully",
          "Eror processing the data"
        );
      }

      return null;
    }
    toast.success(
          "Money Transferred successfully",
          data.data
        );

    return data;
  } catch (error: any) {
    const status = error?.response?.status;

    if (status === 401) {
      try {
        await refreshToken();

        const retry = await axios.post(
          API_ENDPOINTS.TRANSFER,
          {},
          {
            params: {
              receiverUserId,
              amount,
              description,
            },
            withCredentials: true,
          }
        );

        const retryData = retry.data;

        toast.success(
          "Money Transferred successfully",
          retryData.data
        );

        if (retryData?.success === false) {
          return null;
        }

        return retryData;
      } catch {

        return null;
      }
    }
    toast.error(
          "Transfer unSuccessfully",
          "Eror processing the data"
        );

    return null;
  }
};
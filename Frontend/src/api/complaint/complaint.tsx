import axios from "axios";
import API_ENDPOINTS from "../../config/api";
import { refreshToken } from "../auth/refresh";
import { toast } from "../../components/toast/toast";

export const createComplaint = async (
  subject = "",
  description = ""
) => {
  try {
    const res = await axios.post(
      API_ENDPOINTS.CREATE_COMPLAINT,
      {
        subject,
        description,
      },
      {
        withCredentials: true,
      }
    );

    const data = res.data;

    if (data?.success === false) {
      toast.error(
        "Complaint Failed",
        data?.message || "Unable to submit complaint."
      );

      return null;
    }

    toast.success(
      "Complaint Submitted",
      "Your complaint has been submitted successfully."
    );

    return data?.data || true;
  } catch (error: any) {
    const status = error?.response?.status;

    if (status === 401) {
      try {
        await refreshToken();

        const retry = await axios.post(
          API_ENDPOINTS.CREATE_COMPLAINT,
          {
            subject,
            description,
          },
          {
            withCredentials: true,
          }
        );

        const retryData = retry.data;

        if (retryData?.success === false) {
          toast.error(
            "Complaint Failed",
            retryData?.message || "Unable to submit complaint."
          );

          return null;
        }

        toast.success(
          "Complaint Submitted",
          "Your complaint has been submitted successfully."
        );

        return retryData?.data || true;
      } catch {
        toast.error(
          "Authentication Failed",
          "Please login again."
        );

        return null;
      }
    }

    toast.error(
      "Server Error",
      "Something went wrong while submitting complaint."
    );

    return null;
  }
};
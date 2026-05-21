import { notification } from "antd";

type ToastType = "success" | "error" | "info" | "warning";

export const toast = {
  success: (message: string, description?: string) =>
    notification.success({
      message,
      description,
      placement: "top",
    }),

  error: (message: string, description?: string) =>
    notification.error({
      message,
      description,
      placement: "top",
    }),

  info: (message: string, description?: string) =>
    notification.info({
      message,
      description,
      placement: "top",
    }),

  warning: (message: string, description?: string) =>
    notification.warning({
      message,
      description,
      placement: "top",
    }),
};
import { useState } from "react";
import styles from "./TransferForm.module.css";

import { Transfer } from "../../api/home/Transfer";


export default function TransferForm({
  onClose,
}: {
  onClose: () => void;
}) {
  const [receiverUserId, setReceiverUserId] = useState("");
  const [amount, setAmount] = useState("");
  const [description, setDescription] = useState("");

  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      setLoading(true);

      const result = await Transfer(
        Number(receiverUserId),
        Number(amount),
        description
      );

      if (result !== null) {
        onClose();
      }
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.formWrapper}>
      <form className={styles.form} onSubmit={handleSubmit}>
        <h2 className={styles.title}>Transfer Money</h2>

        <input
          className={styles.input}
          placeholder="Receiver User ID"
          value={receiverUserId}
          onChange={(e) => setReceiverUserId(e.target.value)}
        />

        <input
          className={styles.input}
          type="number"
          placeholder="Amount"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
        />

        <input
          className={styles.input}
          placeholder="Description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        />

        <div className={styles.buttonGroup}>
          <button
            className={styles.buttonPrimary}
            type="submit"
            disabled={loading}
          >
            {loading ? "Sending..." : "Send"}
          </button>

          <button
            className={styles.buttonSecondary}
            type="button"
            onClick={onClose}
            disabled={loading}
          >
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
}
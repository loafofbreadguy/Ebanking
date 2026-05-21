import { useState } from "react";
import styles from "./DepositForm.module.css";
import { deposit } from "../../api/home/Deposit";

export default function DepositForm({ onClose }: { onClose: () => void }) {
  const [amount, setAmount] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const numericAmount = Number(amount.trim());

    if (isNaN(numericAmount) || numericAmount <= 0) {
      alert("Enter a valid amount");
      return;
    }

    try {
      setLoading(true);

      const res = await deposit(numericAmount);

      if (res) {
        onClose();
      }
    } catch (err) {
      console.error("Deposit failed", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.formWrapper}>
      <form onSubmit={handleSubmit} className={styles.form}>
        <h2 className={styles.title}>Deposit</h2>

        <input
          className={styles.input}
          placeholder="Enter amount"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
        />

        <div className={styles.buttonGroup}>
          <button type="submit" className={styles.buttonPrimary} disabled={loading}>
            {loading ? "Processing..." : "Deposit"}
          </button>

          <button
            type="button"
            onClick={onClose}
            className={styles.buttonSecondary}
            disabled={loading}
          >
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
}
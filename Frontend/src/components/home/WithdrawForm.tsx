import { useState } from "react";
import styles from "./WithdrawForm.module.css";
import { Withdraw } from "../../api/home/Withdraw";

export default function WithdrawForm({
  onClose,
}: {
  onClose: () => void;
}) {
  const [amount, setAmount] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const numericAmount = Number(amount);

    if (!numericAmount || numericAmount <= 0) {
      alert("Enter a valid amount");
      return;
    }

    try {
      setLoading(true);

      const res = await Withdraw(numericAmount);

      if (res) {
        onClose();
      }

    } catch (err) {
      console.error("Withdraw failed", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.formWrapper}>
      <form className={styles.form} onSubmit={handleSubmit}>
        <h2 className={styles.title}>Withdraw</h2>

        <input
          className={styles.input}
          placeholder="Amount"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
        />

        <div className={styles.buttonGroup}>
          <button
            className={styles.buttonPrimary}
            type="submit"
            disabled={loading}
          >
            {loading ? "Processing..." : "Withdraw"}
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
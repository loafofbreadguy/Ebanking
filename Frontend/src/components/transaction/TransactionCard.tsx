import { useEffect, useState } from "react";
import styles from "./TransactionCard.module.css";
import { transaction } from "../../api/Transaction/Transaction.tsx";

type Transaction = {
  id: string;
  type: string;
  amount: number;
  createdAt: string;
};

export default function TransactionCard() {
  const [data, setData] = useState<Transaction[]>([]);
  const [loading, setLoading] = useState(false);
  const [page, setPage] = useState(0);
  const [last, setLast] = useState(false);

  const fetchTransactions = async (pageNumber = 0) => {
    try {
      setLoading(true);

      const res = await transaction(pageNumber, 10);

      if (!res) return;

      setData(res.transactions);
      setPage(res.currentPage);
      setLast(res.last);
    } catch (err) {
      return ;
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchTransactions(0);
  }, []);

  const handleNext = () => {
    if (!last) {
      fetchTransactions(page + 1);
    }
  };

  const handlePrev = () => {
    if (page > 0) {
      fetchTransactions(page - 1);
    }
  };

  return (
    <div className={styles.wrapper}>
      <h2 className={styles.title}>Transaction History</h2>

      {loading && <p className={styles.info}>Loading...</p>}

      {!loading && data.length === 0 && (
        <p className={styles.info}>No transactions found</p>
      )}

      <div className={styles.list}>
        {data.map((tx) => (
          <div key={tx.id} className={styles.card}>
            <div className={styles.left}>
              <p className={styles.type}>{tx.type}</p>
              <p className={styles.date}>
                {new Date(tx.createdAt).toLocaleString()}
              </p>
            </div>

            <div
              className={
                tx.type?.toLowerCase() === "deposit"
                  ? styles.amountPositive
                  : styles.amountNegative
              }
            >
              {tx.type?.toLowerCase() === "deposit" ? "+" : "-"}
              {tx.amount}
            </div>
          </div>
        ))}
      </div>

      <div className={styles.pagination}>
        <button onClick={handlePrev} disabled={page === 0 || loading}>
          Prev
        </button>

        <span>Page {page + 1}</span>

        <button onClick={handleNext} disabled={last || loading}>
          Next
        </button>
      </div>
    </div>
  );
}
import { Balance } from "../../api/home/Balance";
import "./BalanceCard.css";
import { useEffect, useState } from "react";
import { EyeInvisibleOutlined, EyeOutlined } from "@ant-design/icons";

const BalanceCard = () => {
  const [balance, setBalance] = useState<number>(0);
  const [visible, setVisible] = useState<boolean>(false);

  const fetchBalance = async () => {
    const value = await Balance();
    if (value !== null) {
      setBalance(value);
    }
  };

  useEffect(() => {
    fetchBalance();
  }, []);

  const toggleVisibility = async () => {
    setVisible((prev) => !prev);
    await fetchBalance();
  };

  return (
    <div className="balance-card">
      <div className="balance-header">
        <p className="balance-title">Current Balance</p>

        <span className="eye-icon" onClick={toggleVisibility}>
          {visible ? <EyeInvisibleOutlined /> : <EyeOutlined />}
        </span>
      </div>

      <h1 className="balance-amount">
        {visible ? balance : "*****"}
      </h1>
    </div>
  );
};

export default BalanceCard;
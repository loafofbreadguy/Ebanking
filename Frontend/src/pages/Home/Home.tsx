import viteLogo from "../../../public/favicon.svg";
import BalanceCard from "../../components/home/BalanceCard";
import QuickActions from "../../components/home/QuickActions";
import TransferForm from "../../components/home/TransferForm";
import WithdrawForm from "../../components/home/WithdrawForm";
import DepositForm from "../../components/home/DepositForm";

import "./Home.css";
import Footer from "../../components/footer/Footer";
import { useState } from "react";

export default function Home() {
  const [activeModal, setActiveModal] =
    useState<null | "transfer" | "withdraw" | "deposit">(null);

  const closeModal = () => setActiveModal(null);

  return (
    <div className="page">

      <div className="home-container">

        <div className="home-header">
          <h1>
            Lightning Pay <img src={viteLogo} className="vite-logo" />
          </h1>
          <p>Money Transfer Made Easy</p>
        </div>

        <div className="section">
          <BalanceCard />
        </div>

        <div className="section">
          <QuickActions
            onTransfer={() => setActiveModal("transfer")}
            onWithdraw={() => setActiveModal("withdraw")}
            onDeposit={() => setActiveModal("deposit")}
          />
        </div>

        {activeModal && (
          <div className="section inline-form">
            {activeModal === "transfer" && (
              <TransferForm onClose={closeModal} />
            )}

            {activeModal === "withdraw" && (
              <WithdrawForm onClose={closeModal} />
            )}

            {activeModal === "deposit" && (
              <DepositForm onClose={closeModal} />
            )}
          </div>
        )}

      </div>

      <Footer />

    </div>
  );
}
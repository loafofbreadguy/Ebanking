import {
  SwapOutlined,
  ArrowUpOutlined,
  ArrowDownOutlined,
} from "@ant-design/icons";

import "./QuickActions.css";

type Props = {
  onTransfer: () => void;
  onWithdraw: () => void;
  onDeposit: () => void;
};

const QuickActions: React.FC<Props> = ({ onTransfer, onWithdraw, onDeposit }) => {
  return (
    <div className="actions-container">

      <div className="action-card" onClick={onTransfer}>
        <SwapOutlined className="action-icon" />
        <span>Transfer</span>
      </div>

      <div className="action-card" onClick={onWithdraw}>
        <ArrowUpOutlined className="action-icon" />
        <span>Withdraw</span>
      </div>

      <div className="action-card" onClick={onDeposit}>
        <ArrowDownOutlined className="action-icon" />
        <span>Deposit</span>
      </div>

    </div>
  );
};

export default QuickActions;
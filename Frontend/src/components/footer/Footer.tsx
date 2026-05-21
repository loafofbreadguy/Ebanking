import {
  HomeOutlined,
  FileTextOutlined,
  QuestionCircleOutlined,
  UserOutlined,
} from "@ant-design/icons";

import { NavLink } from "react-router-dom";
import "./Footer.css";
import routes from "../../routes/routes";

const Footer = () => {
  return (
    <footer className="footer">
      <div className="footer-container">

        <NavLink to={routes.home} className="footer-item">
          <HomeOutlined className="footer-icon" />
          <span>Home</span>
        </NavLink>

        <NavLink to={routes.trasaction} className="footer-item">
          <FileTextOutlined className="footer-icon" />
          <span>Transaction</span>
        </NavLink>

        <NavLink to={routes.complaint} className="footer-item">
          <QuestionCircleOutlined className="footer-icon" />
          <span>Complaint</span>
        </NavLink>

        <NavLink to={routes.user} className="footer-item">
          <UserOutlined className="footer-icon" />
          <span>User</span>
        </NavLink>

      </div>
    </footer>
  );
};

export default Footer;
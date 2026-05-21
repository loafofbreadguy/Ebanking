import { Routes, Route } from "react-router-dom";

import routes from "./routes.tsx";

import Home from "../pages/Home/Home.tsx";
import Login from "../pages/Login/Login.tsx";
import Register from "../pages/Register/Register.tsx";
import Complaint from "../pages/Complaint/Complaint.tsx";

import ProtectedRoute from "./ProtectedRoute";
import Transaction from "../pages/Transactions/Transaction.tsx";
import User from "../pages/User/User.tsx";

const AppRoutes = () => {
  return (
    <Routes>

      {/* Public Routes */}
      <Route path={routes.login} element={<Login />} />
      <Route path={routes.register} element={<Register />} />

      {/* Protected Routes */}
      <Route element={<ProtectedRoute />}>
        <Route path={routes.home} element={<Home />} />
        <Route path={routes.trasaction} element={<Transaction />} />
        <Route path={routes.complaint} element={<Complaint />} />
        <Route path={routes.user} element={<User />} />
      </Route>

    </Routes>
  );
};

export default AppRoutes;
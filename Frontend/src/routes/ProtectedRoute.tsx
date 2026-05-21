import { Navigate, Outlet } from "react-router-dom";
import { useEffect, useState } from "react";

import { checkAuth } from "../api/auth/auth";

const ProtectedRoute = () => {

  const [loading, setLoading] = useState(true);
  const [authenticated, setAuthenticated] = useState(false);

  useEffect(() => {

    const verify = async () => {

      const user = await checkAuth();

      setAuthenticated(!!user);

      setLoading(false);
    };

    verify();

  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (!authenticated) {
    return <Navigate to="/login" replace />;
  }

  return <Outlet />;
};

export default ProtectedRoute;
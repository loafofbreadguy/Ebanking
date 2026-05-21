import { useState } from "react";
import { login } from "../../api/auth/login";
import "./Login.css";
import { useNavigate } from "react-router-dom";
import routes from "../../routes/routes";
import { toast } from "../../components/toast/toast";

const Login = () => {
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>("");

  const navigate = useNavigate();

  const handleLogin = async (e: React.FormEvent) => {
  e.preventDefault();
  setLoading(true);
  setError("");

  const res = await login({ email, password });

  setLoading(false);

  if (!res.success) {
    setError(res.message);

    toast.error("Login failed", res.message);
    return;
  }

  toast.success("Success", res.message);

  navigate(routes.home);
};

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleLogin}>
        <h2>Welcome Back</h2>
        <p className="subtitle">Login to continue</p>

        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        {error && <p className="error">{error}</p>}

        <button type="submit" disabled={loading}>
          {loading ? "Logging in..." : "Login"}
        </button>

        <div className="login-links">
          <a href="/forgot-password">Forgot password?</a>
          <a href="/register">Create account</a>
        </div>
      </form>
    </div>
  );
};

export default Login;
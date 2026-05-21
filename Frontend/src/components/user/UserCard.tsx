import { useEffect, useState } from "react";
import { user } from "../../api/user/User";

type UserType = {
  id: number;
  name: string;
  email: string;
  role: string;
};

export default function UserCard() {
  const [data, setData] = useState<UserType | null>(null);

  useEffect(() => {
    const fetchUser = async () => {
      const res = await user();

      if (res) {
        setData(res);
      }
    };

    fetchUser();
  }, []);

  return (
    <div style={styles.container}>
      <div style={styles.card}>
        <div style={styles.avatar}>
          {data?.name?.charAt(0).toUpperCase()}
        </div>

        <h2 style={styles.name}>{data?.name}</h2>

        <p style={styles.email}>Email : {data?.email}</p>

        <p style={styles.email}>UserId : {data?.id}</p>

        <span style={styles.role}>{data?.role}</span>
      </div>
    </div>
  );
}

const styles: { [key: string]: React.CSSProperties } = {
  container: {
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    minHeight: "100vh",
    background: "#f4f7fb",
  },

  card: {
    width: "350px",
    background: "#fff",
    borderRadius: "20px",
    padding: "30px",
    boxShadow: "0 10px 25px rgba(0,0,0,0.1)",
    textAlign: "center",
    transition: "0.3s ease",
  },

  avatar: {
    width: "90px",
    height: "90px",
    borderRadius: "50%",
    background: "linear-gradient(135deg, #4f46e5, #7c3aed)",
    color: "white",
    fontSize: "36px",
    fontWeight: "bold",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    margin: "0 auto 20px auto",
  },

  name: {
    margin: "10px 0",
    fontSize: "28px",
    color: "#111827",
  },

  email: {
    color: "#6b7280",
    marginBottom: "20px",
    fontSize: "15px",
  },

  role: {
    background: "#eef2ff",
    color: "#4338ca",
    padding: "8px 16px",
    borderRadius: "999px",
    fontSize: "14px",
    fontWeight: 600,
  },
};
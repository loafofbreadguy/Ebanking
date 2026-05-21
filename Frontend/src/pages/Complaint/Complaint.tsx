import Footer from "../../components/footer/Footer";
import ComplaintCard from "../../components/Complaints/ComplaintCard";
import styles from "./Complaint.module.css";

export default function Complaint() {
  return (
    <div className={styles.page}>
      <ComplaintCard />
      <Footer />
    </div>
  );
}
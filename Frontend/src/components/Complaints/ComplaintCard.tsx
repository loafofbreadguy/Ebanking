import { useState } from "react";
import styles from "./ComplaintCard.module.css";
import { createComplaint } from "../../api/complaint/complaint";

export default function ComplaintCard() {
  const [subject, setSubject] = useState("");
  const [description, setDescription] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: any) => {
    e.preventDefault();

    if (!subject.trim() || !description.trim()) return;

    setLoading(true);

    const res = await createComplaint(subject, description);

    if (res) {
      setSubject("");
      setDescription("");
    }

    setLoading(false);
  };

  return (
    <div className={styles.card}>
      <h2 className={styles.title}>Create Complaint</h2>

      <form className={styles.form} onSubmit={handleSubmit}>
        <div className={styles.field}>
          <label>Subject</label>

          <input
            type="text"
            placeholder="Enter complaint subject"
            value={subject}
            onChange={(e) => setSubject(e.target.value)}
          />
        </div>

        <div className={styles.field}>
          <label>Description</label>

          <textarea
            rows={6}
            placeholder="Describe your issue"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          />
        </div>

        <button type="submit" disabled={loading}>
          {loading ? "Submitting..." : "Submit Complaint"}
        </button>
      </form>
    </div>
  );
}
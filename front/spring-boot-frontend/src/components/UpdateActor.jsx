import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

function UpdateActor() {
  const { id } = useParams();
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchActorDetails = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/home/actor/${id}`);
        const actor = response.data;
        setFirstName(actor.firstName);
        setLastName(actor.lastName);
      } catch (error) {
        console.error('Failed to fetch actor details:', error);
      }
    };
    fetchActorDetails();
  }, [id]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.put(`http://localhost:8080/home/updateActor/${id}`, { firstName, lastName });
      navigate('/home/actors'); // Redirect after updating
    } catch (error) {
      console.error('Failed to update actor:', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Update Actor</h2>
      <div>
        <label>First Name:</label>
        <input type="text" value={firstName} onChange={(e) => setFirstName(e.target.value)} />
      </div>
      <div>
        <label>Last Name:</label>
        <input type="text" value={lastName} onChange={(e) => setLastName(e.target.value)} />
      </div>
      <button type="submit">Update Actor</button>
    </form>
  );
}

export default UpdateActor;

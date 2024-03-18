import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'; // Import useNavigate for redirection

function ActorList() {
  const [actors, setActors] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 20;
  let totalPages = Math.ceil(actors.length / itemsPerPage);
  const navigate = useNavigate(); // Use navigate for redirection

  useEffect(() => {
    const fetchActors = async () => {
      try {
        const response = await axios.get('http://localhost:8080/home/allActors');
        setActors(response.data);
      } catch (error) {
        console.error('Failed to fetch actors:', error);
      }
    };
    fetchActors();
  }, []);

  const actorsToShow = actors.slice(
    (currentPage - 1) * itemsPerPage,
    currentPage * itemsPerPage
  );

  const handlePrevClick = () => {
    setCurrentPage((prevPage) => Math.max(prevPage - 1, 1));
  };

  const handleNextClick = () => {
    setCurrentPage((prevPage) => Math.min(prevPage + 1, totalPages));
  };

  const deleteActor = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/home/deleteActor/${id}`);
      setActors(actors.filter(actor => actor.actorID !== id));
    } catch (error) {
      console.error('Failed to delete actor:', error);
    }
  };

  return (
    <div>
      <h2>Actor List</h2>
      {actorsToShow.map((actor) => (
        <div key={actor.actorID}>
          <h3>{actor.firstName} {actor.lastName}</h3>
          <button onClick={() => deleteActor(actor.actorID)}>Delete</button>
          <button onClick={() => navigate(`/updateActor/${actor.actorID}`)}>Update</button>
        </div>
      ))}
      <div>
        <button onClick={handlePrevClick} disabled={currentPage === 1}>Previous</button>
        <span> Page {currentPage} of {totalPages} </span>
        <button onClick={handleNextClick} disabled={currentPage === totalPages}>Next</button>
      </div>
    </div>
  );
}

export default ActorList;

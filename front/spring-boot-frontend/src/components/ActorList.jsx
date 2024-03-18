import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';

function ActorList() {
  const [actors, setActors] = useState([]);
  const [searchQuery, setSearchQuery] = useState(''); // State to track search query
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 20;
  const navigate = useNavigate();

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

  // Apply search filter to actors
  const filteredActors = searchQuery
    ? actors.filter(actor =>
        `${actor.firstName} ${actor.lastName}`.toLowerCase().includes(searchQuery.toLowerCase())
      )
    : actors;

  let totalPages = Math.ceil(filteredActors.length / itemsPerPage);
  const actorsToShow = filteredActors.slice(
    (currentPage - 1) * itemsPerPage,
    currentPage * itemsPerPage
  );

  const handlePrevClick = () => setCurrentPage((prevPage) => Math.max(prevPage - 1, 1));
  const handleNextClick = () => setCurrentPage((prevPage) => Math.min(prevPage + 1, totalPages));

  const deleteActor = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/home/deleteActor/${id}`);
      setActors(filteredActors.filter(actor => actor.actorID !== id));
    } catch (error) {
      console.error('Failed to delete actor:', error);
    }
  };

  return (
    <div>
      <h2>Actor List</h2>
      <div>
        {/* Search bar */}
        <input
          type="text"
          placeholder="Search actors by name..."
          value={searchQuery}
          onChange={(e) => {
            setSearchQuery(e.target.value);
            setCurrentPage(1); // Reset to first page on new search
          }}
        />
      </div>
      <p></p>
      <div>
        <Link to="/addActor">Add New Actor</Link>
      </div>
      {actorsToShow.map((actor) => (
        <div key={actor.actorID}>
          <h3>
            <Link to={`/actorMovies/${actor.actorID}`} style={{ textDecoration: 'none', color: 'white' }}>
              {actor.firstName} {actor.lastName}
            </Link>
          </h3>
          <button onClick={() => deleteActor(actor.actorID)}>Delete</button>
          <button onClick={() => navigate(`/updateActor/${actor.actorID}`)}>Update</button>
        </div>
      ))}
      <p></p>
      <div>
        <button onClick={handlePrevClick} disabled={currentPage === 1}>Previous</button>
        <span> Page {currentPage} of {totalPages} </span>
        <button onClick={handleNextClick} disabled={currentPage === totalPages}>Next</button>
      </div>
    </div>
  );
}

export default ActorList;

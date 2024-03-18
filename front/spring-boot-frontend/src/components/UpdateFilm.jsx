import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

function UpdateFilm() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [languages, setLanguages] = useState([]);
  const [selectedLanguageId, setSelectedLanguageId] = useState('');
  const [releaseYear, setReleaseYear] = useState('');

  useEffect(() => {
    const fetchFilmDetails = async () => {
      try {
        const filmResponse = await axios.get(`http://localhost:8080/home/film/${id}`);
        setTitle(filmResponse.data.title);
        setDescription(filmResponse.data.description);
        setSelectedLanguageId(filmResponse.data.languageID?.toString() ?? '');
        setReleaseYear(filmResponse.data.releaseYear?.toString() ?? '');
      } catch (error) {
        console.error('Failed to fetch film details:', error);
      }
    };


    const fetchLanguages = async () => {
      try {
        const languagesResponse = await axios.get('http://localhost:8080/home/allLanguages');
        setLanguages(languagesResponse.data);
      } catch (error) {
        console.error('Failed to fetch languages:', error);
      }
    };

    fetchFilmDetails();
    fetchLanguages();
  }, [id]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.put(`http://localhost:8080/home/updateFilm/${id}`, {
        title,
        description,
        languageID: parseInt(selectedLanguageId, 10),
        releaseYear: parseInt(releaseYear, 10),
      });
      navigate('/home');
    } catch (error) {
      console.error('Failed to update film:', error);
    }
  };
  

  return (
    <form onSubmit={handleSubmit}>
      <h2>Update Film</h2>
      <div>
        <label>Title:</label>
        <input type="text" value={title} onChange={(e) => setTitle(e.target.value)} />
      </div>
      <div>
        <label>Description:</label>
        <input type="text" value={description} onChange={(e) => setDescription(e.target.value)} />
      </div>
      <div>
        <label>Language:</label>
        <select value={selectedLanguageId} onChange={(e) => setSelectedLanguageId(e.target.value)}>
          {languages.map(language => (
            <option key={language.languageId} value={language.languageId}>
              {language.name}
            </option>
          ))}
        </select>
      </div>
      <div>
        <label>Release Year:</label>
        <input
          type="number" //ensure users can only enter numbers
          value={releaseYear}
          onChange={(e) => setReleaseYear(e.target.value)}
        />
      </div>
      <button type="submit">Update Film</button>
    </form>
  );
}

export default UpdateFilm;

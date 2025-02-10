import React from 'react';

const Footer = () => {
  return (
    <div className="container-fluid p-1 text-black fixed-bottom">
      <p
        className="fs-6 text-center"
        style={{
          transition: 'text-shadow 0.3s ease-in-out',
          cursor: 'pointer',
        }}
        onMouseEnter={(e) => {
          e.target.style.textShadow = '0 0 10px rgba(255, 255, 255, 0.8), 0 0 20px rgba(255, 255, 255, 0.8), 0 0 30px rgba(255, 255, 255, 0.8)';
        }}
        onMouseLeave={(e) => {
          e.target.style.textShadow = 'none';
        }}
      >
        Made with ❤️ by Team <strong>07</strong>
      </p>
    </div>
  );
};

export { Footer };

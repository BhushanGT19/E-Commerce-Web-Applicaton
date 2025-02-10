const capitalize = async (str) => {
  // Check if the string is empty or null
  if (!str) return str;

  // Capitalize the first letter and concatenate with the rest of the string
  return str.charAt(0).toUpperCase() + str.slice(1);
};

module.exports = { capitalize };

function calculateJobColorString(id) {
  let hue = Math.round(((4234.175 * Math.abs(Math.sin(2423417465.234 * Math.sin(id) * Math.sin(id)))) % 1) * 360);
  let lightness = 45
  if (hue > 40 && hue < 200) {
    lightness = 40;
  }
  return `hsl(${hue} 100 ${lightness})`
}
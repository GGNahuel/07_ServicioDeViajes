import { Image } from "../types/ApiTypes";

export function generateImageURL(image : Image) {
  const imageSrc = `data:${image.contentType};base64,${image.data}`;

  return imageSrc
}
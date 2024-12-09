
export interface LocationDto {
  id: number;
  latitude: number; // Correspond à BigDecimal en Java
  longitude: number; // Correspond à BigDecimal en Java
  address: string;
  cityReport: CityReportDto; // On suppose que tu as un modèle pour CityReportDto
}

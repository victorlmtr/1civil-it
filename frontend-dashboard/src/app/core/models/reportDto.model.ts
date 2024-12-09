export interface ReportDto {
  id: number;
  userid: number;
  creationdate: string;
  comment: string;
  typeid: ReporttypeDto;
  locationid: LocationDto;
  pictures: PictureDto[];
}

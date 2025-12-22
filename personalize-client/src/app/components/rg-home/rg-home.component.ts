import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AddTileDialogComponent } from '../add-tile-dialog/add-tile-dialog.component';

@Component({
  selector: 'app-rg-home',
  templateUrl: './rg-home.component.html',
  styleUrl: './rg-home.component.css'
})
export class RgHomeComponent {

  person:string ='';
  loginSuccess = '';
  selectedMenuItem = 'Unified Dashboard'; // default selected
  tiles: Array<{ id: number; platform?: string; url?: string; username?: string; password?: string }> = [];
  pageSize = 6;
  currentPage = 1;

  constructor(private dialog: MatDialog){ }

  ngOnInit() {
    try {
      const msg = sessionStorage.getItem('login_success');
      if (msg) {
        this.loginSuccess = msg;
        sessionStorage.removeItem('login_success');
      }
    } catch (e) {}

    // initialize with one plus tile prompt (no tiles yet)
    this.tiles = [];
  }

  addPerson(person:string){
    alert('in progreess'+person);
  }

  selectMenuItem(item: string) {
    this.selectedMenuItem = item;
  }

  // Tiles and pagination helpers
  addTile() {
    this.openAddDialog();
  }

  get totalPages(): number {
    const effectivePageSize = Math.max(1, this.pageSize - 1); // reserve 1 slot for add tile tile
    return Math.max(1, Math.ceil(this.tiles.length / effectivePageSize));
  }

  get pagedTiles(): Array<{ id: number; platform?: string }> {
    const effectivePageSize = Math.max(1, this.pageSize - 1); // add-tile occupies first slot
    const start = (this.currentPage - 1) * effectivePageSize;
    return this.tiles.slice(start, start + effectivePageSize);
  }

  prevPage() {
    if (this.currentPage > 1) this.currentPage--;
  }

  nextPage() {
    if (this.currentPage < this.totalPages) this.currentPage++;
  }

  // Open dialog to edit an existing tile's details
  openTileDialog(tile: { id: number; platform?: string; url?: string; username?: string; password?: string }) {
    const dialogRef = this.dialog.open(AddTileDialogComponent, {
      width: '420px',
      maxWidth: '90vw',
      panelClass: 'centered-dialog'
    });
    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      const { url, username, password } = result as { url: string; username: string; password: string };
      tile.url = url;
      tile.username = username;
      tile.password = password;
      tile.platform = this.detectPlatformFromUrl(url);
    });
  }

  // Basic icon resolver (placeholder icons)
  platformIcon(platform?: string): string {
    switch (platform) {
      case 'facebook': return 'f';
      case 'twitter': return 't';
      case 'instagram': return '◎';
      case 'linkedin': return 'in';
      case 'youtube': return '▶';
      default: return '+';
    }
  }

  // Open Material dialog for adding a tile
  openAddDialog() {
    const dialogRef = this.dialog.open(AddTileDialogComponent, {
      width: '420px',
      maxWidth: '90vw',
      panelClass: 'centered-dialog'
      // Angular Material dialogs are centered by default
    });
    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      const { url, username, password } = result as { url: string; username: string; password: string };
      const nextId = (this.tiles.length ? Math.max(...this.tiles.map(t => t.id)) : 0) + 1;
      const platform = this.detectPlatformFromUrl(url);
      this.tiles.push({ id: nextId, url, username, password, platform });
      this.currentPage = this.totalPages;
    });
  }

  // Message listener removed; dialog returns data via afterClosed

  detectPlatformFromUrl(url?: string): string | undefined {
    if (!url) return undefined;
    const u = url.toLowerCase();
    if (u.includes('facebook.com')) return 'facebook';
    if (u.includes('twitter.com') || u.includes('x.com')) return 'twitter';
    if (u.includes('instagram.com')) return 'instagram';
    if (u.includes('linkedin.com')) return 'linkedin';
    if (u.includes('youtube.com') || u.includes('youtu.be')) return 'youtube';
    return undefined;
  }
}

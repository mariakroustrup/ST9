% bar plot

EnK = 82.1
ToR = 10.3
TreR = 2.6
FireR = 2.6
FemR = 2.6

figure
bK1 = [EnK;ToR;TreR;FireR;FemR]
cK1 = categorical({'Forkert lægemiddel', 'Forkert formulering', 'Forkert dosis', 'Forkert administrationsvej', 'Udeladelsese af dosis'});
cK1 = reordercats(cK1,{'Forkert lægemiddel', 'Forkert formulering', 'Forkert dosis', 'Forkert administrationsvej', 'Udeladelsese af dosis'});
bar(cK1,bK1)
title('Typer af utilsigtede hændelser ved kontraktskift')
ylabel('Antal utilsigtede hændelser (%)')
ylim([0 100])
labels = arrayfun(@(value) num2str(value,'%2.1f'),bK1,'UniformOutput',false);
    text(cK1,bK1,labels,'HorizontalAlignment','center','VerticalAlignment','bottom') 
set(gca,'fontsize',20)

%% Kontraktskift

EnKD = 27
EnKP = 6
ToKD= 6
ToKP= 2
TreKD = 4
TreKP = 2
FireKD = 3
FireKP = 2
FemKD = 2
FemKP = 1
SeksKD = 1
SeksKP = 0

figure
bK1 = [EnKD, EnKP; ToKD, ToKP; TreKD, TreKP; FireKD, FireKP; FemKD, FemKP; SeksKD, SeksKP]
cK1 = categorical({'Look-a-like', 'Forkert lægemiddel ordineret', 'Dobbeltordination', 'Sound-a-like', 'Forkert dosis', 'Forsinket indgift'});
cK1 = reordercats(cK1,{'Look-a-like', 'Forkert lægemiddel ordineret', 'Dobbeltordination', 'Sound-a-like', 'Forkert dosis', 'Forsinket indgift'});
bar(cK1,bK1)
b = bar(bK1)
title('Typer af utilsigtede hændelser relateret til kontraktskift')
ylabel('Antal utilsigtede hændelser (%)')
ylim([0 30])
% labels = arrayfun(@(value) num2str(value,'%2.1f'),bK1,'UniformOutput',false);
% text(cK1,bK1,labels,'HorizontalAlignment','center','VerticalAlignment','bottom') 
width = b.BarWidth;
for i=1:length(bK1(:, 1))
    row = bK1(i, :);
    % 0.5 is approximate net width of white spacings per group
    offset = ((width + 0.5) / length(row)) / 2;
    cK1 = linspace(i-offset, i+offset, length(row));
    text(cK1,row,num2str(row'),'vert','bottom','horiz','center');
end
set(gca,'fontsize',20)

%% Restordre

% EnR = 82.1
% ToR = 10.3
% TreR = 2.6
% FireR = 2.6
% FemR = 2.6
% 
% figure
% bK1 = [EnR;ToR;TreR;FireR;FemR]
% %c = categorical({'Forkert lægemiddel', 'Forkert formulering', 'Forkert dosis', 'Forkert administrationsvej', 'Udeladelsese af dosis'});
% cK1 = reordercats(cK1,{'Forkert lægemiddel', 'Forkert formulering', 'Forkert dosis', 'Forkert administrationsvej', 'Udeladelsese af dosis'});
% bar(cK1,bK1)
% title('Typer af utilsigtede hændelser ved kontraktskift')
% ylabel('Antal utilsigtede hændelser (%)')
% ylim([0 100])
% labels = arrayfun(@(value) num2str(value,'%2.1f'),bK1,'UniformOutput',false);
%     text(cK1,bK1,labels,'HorizontalAlignment','center','VerticalAlignment','bottom') 
% set(gca,'fontsize',20)
% % subplot(2,1,2)
% % bar(c,min)
% % title('Minimum force')
% % ylabel('Force [N]')
